// Client logic to interact with Spring Boot GraphQL layer
const GRAPHQL_ENDPOINT = '/graphql';

// HTML Elements
const tbody = document.getElementById('vehicle-list-body');
const loadingState = document.getElementById('loading-state');
const totalCountEl = document.getElementById('total-vehicles-count');
const avgPriceEl = document.getElementById('avg-price-stat');

// Modal Elements
const addModal = document.getElementById('add-modal');
const addBtn = document.getElementById('add-vehicle-btn');
const closeBtn = document.getElementById('close-modal-btn');
const cancelBtn = document.getElementById('cancel-modal-btn');
const form = document.getElementById('add-vehicle-form');

// Basic GraphQL Client Fetcher
async function fetchGraphQL(query, variables = {}) {
    try {
        const response = await fetch(GRAPHQL_ENDPOINT, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ query, variables })
        });
        const result = await response.json();
        
        if (result.errors) {
            console.error('GraphQL Error:', result.errors);
            throw new Error(result.errors[0].message);
        }
        return result.data;
    } catch (err) {
        console.error('Network/Request Error:', err);
        alert('Action Failed: ' + err.message);
        return null;
    }
}

// Queries
const GET_ALL_VEHICLES_QUERY = `
  query {
    getAllVehicles {
      id
      make
      model
      year
      color
      price
    }
  }
`;

const CREATE_VEHICLE_MUTATION = `
  mutation CreateVehicle($vehicle: VehicleInput!) {
    createVehicle(vehicle: $vehicle) {
      id
      make
      model
      year
    }
  }
`;

const DELETE_VEHICLE_MUTATION = `
  mutation DeleteVehicle($id: ID!) {
    deleteVehicle(id: $id)
  }
`;

// Initialize Page
async function loadVehicles() {
    loadingState.classList.remove('hidden');
    const data = await fetchGraphQL(GET_ALL_VEHICLES_QUERY);
    loadingState.classList.add('hidden');

    if (data && data.getAllVehicles) {
        renderVehicles(data.getAllVehicles);
        updateStats(data.getAllVehicles);
    }
}

// Render data into the DOM
function renderVehicles(vehicles) {
    tbody.innerHTML = '';
    
    if (vehicles.length === 0) {
        tbody.innerHTML = `<tr><td colspan="6" style="text-align:center; padding: 40px; color: var(--text-secondary);">No vehicles in inventory. Add one to get started!</td></tr>`;
        return;
    }

    vehicles.forEach((v, index) => {
        const row = document.createElement('tr');
        // stagger animation class
        row.className = `stagger-${(index % 3) + 1}`; 
        
        row.innerHTML = `
            <td><strong>${v.make}</strong></td>
            <td>${v.model}</td>
            <td>${v.year}</td>
            <td>${v.color || 'N/A'}</td>
            <td class="price-cell">$${v.price ? v.price.toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2}) : '0.00'}</td>
            <td>
                <button class="danger-text-btn" onclick="deleteVehicle(${v.id})">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function updateStats(vehicles) {
    totalCountEl.textContent = vehicles.length;
    
    if (vehicles.length === 0) {
        avgPriceEl.textContent = '$0';
        return;
    }
    
    let sum = vehicles.reduce((acc, curr) => acc + (curr.price || 0), 0);
    let avg = sum / vehicles.length;
    avgPriceEl.textContent = '$' + Math.round(avg).toLocaleString();
}

// Form Submission (Add Vehicle)
form.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const make = document.getElementById('make').value;
    const model = document.getElementById('model').value;
    const year = parseInt(document.getElementById('year').value, 10);
    const price = parseFloat(document.getElementById('price').value);
    const color = document.getElementById('color').value;

    const vehicleInput = { make, model, year, price, color };

    addModal.classList.add('hidden');
    loadingState.classList.remove('hidden');

    const result = await fetchGraphQL(CREATE_VEHICLE_MUTATION, { vehicle: vehicleInput });
    
    if (result && result.createVehicle) {
        form.reset();
        await loadVehicles(); // Reload to get fresh list
    } else {
        loadingState.classList.add('hidden');
    }
});

// Delete Record
window.deleteVehicle = async function(id) {
    if(!confirm('Are you sure you want to delete this vehicle?')) return;
    
    loadingState.classList.remove('hidden');
    const result = await fetchGraphQL(DELETE_VEHICLE_MUTATION, { id: id });
    
    if(result && result.deleteVehicle) {
        await loadVehicles(); // Refresh UI
    } else {
        loadingState.classList.add('hidden');
    }
}

// Modal Toggle Logic
addBtn.addEventListener('click', () => {
    addModal.classList.remove('hidden');
    document.getElementById('make').focus();
});

const closeModal = () => addModal.classList.add('hidden');
closeBtn.addEventListener('click', closeModal);
cancelBtn.addEventListener('click', closeModal);
window.addEventListener('click', (e) => {
    if (e.target === addModal) closeModal();
});

// Boot the app
loadVehicles();
