<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Banco Eurekabanck - Ubicación</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <!-- Cambié el callback a "initMap" de forma correcta y defer el script de Google Maps -->
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCUE-yogna3o0kW11sYvxncJwiGPv0sFI4&libraries=places&callback=initMap" async defer></script>
    <style>
        #map { height: 500px; width: 100%; border-radius: 10px; }
    </style>
</head>
<body class="bg-gray-100 text-gray-900">
    <div class="max-w-4xl mx-auto p-6">
        <h1 class="text-3xl font-bold text-center text-blue-600 mb-4">Banco Eurekabanck</h1>
        <div class="bg-white shadow-md rounded-lg p-6">
            <label for="branchSelect" class="block text-lg font-medium">Selecciona una sucursal:</label>
            <select id="branchSelect" class="w-full p-2 border border-gray-300 rounded mt-2"></select>
            <div class="flex justify-between mt-4">
                <button onclick="calculateSelectedRoute()" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Calcular Ruta</button>
                <button onclick="calculateOptimalRoute()" class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">Banco más Cercano</button>
            </div>
            <div id="routeInfo" class="text-lg font-semibold mt-4 text-center"></div>
        </div>
        <div id="map" class="mt-6 shadow-lg"></div>
    </div>

    <script>
        let map, service, display, userLocation, userMarker;
        let branches = [
            { name: "San Luis Shopping", lat: -0.308215, lng: -78.449646 },
            { name: "Quicentro Sur", lat: -0.285728, lng: -78.542878 },
            { name: "Recreo", lat: -0.252530, lng: -78.522314 },
            { name: "Quicentro (Carolina)", lat: -0.1757, lng: -78.4801 },
            { name: "Scala Shopping", lat: -0.207164, lng: -78.426300 },
            { name: "Paseo San Francisco", lat: -0.1966, lng: -78.4361 },
            { name: "Aeropuerto", lat: -0.1225, lng: -78.3546 },
            { name: "RioCentro Norte", lat: -2.1244, lng: -79.9061 }
        ];

        // Definir la función initMap globalmente
        function initMap() {
            map = new google.maps.Map(document.getElementById("map"), { zoom: 12, center: { lat: -0.19, lng: -78.48 } });
            service = new google.maps.DirectionsService();
            display = new google.maps.DirectionsRenderer();
            display.setMap(map);
            
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(position => {
                    userLocation = { lat: position.coords.latitude, lng: position.coords.longitude };
                    userMarker = new google.maps.Marker({
                        position: userLocation,
                        map: map,
                        title: "Tu ubicación",
                        icon: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png"
                    });
                    fillBranchOptions();
                }, error => {
                    alert("Error al obtener tu ubicación.");
                });
            } else {
                alert("Tu navegador no soporta geolocalización.");
            }
        }

        function fillBranchOptions() {
            let select = document.getElementById("branchSelect");
            select.innerHTML = '';
            
            branches.forEach(branch => {
                let option = document.createElement("option");
                option.value = JSON.stringify(branch);
                option.textContent = branch.name;
                select.appendChild(option);
            });
        }

        function calculateOptimalRoute() {
            if (!userLocation) {
                alert("Ubicación no disponible.");
                return;
            }

            Promise.all(branches.map(branch => getRouteData(branch))).then(results => {
                let bestRoute = results.sort((a, b) => a.duration - b.duration)[0];
                displayRoute(bestRoute);
            });
        }

        function getRouteData(branch) {
            return new Promise(resolve => {
                service.route({
                    origin: userLocation,
                    destination: { lat: branch.lat, lng: branch.lng },
                    travelMode: google.maps.TravelMode.DRIVING
                }, (response, status) => {
                    if (status === google.maps.DirectionsStatus.OK) {
                        let duration = response.routes[0].legs[0].duration.text;
                        let distance = response.routes[0].legs[0].distance.text;
                        resolve({ branch, response, duration, distance });
                    } else {
                        resolve({ branch, response: null, duration: "N/A", distance: "N/A" });
                    }
                });
            });
        }

        function displayRoute(routeData) {
            if (routeData.response) {
                display.setDirections(routeData.response);
                document.getElementById("routeInfo").textContent = `Duración: ${routeData.duration}, Distancia: ${routeData.distance}`;
            } else {
                alert("No se pudo calcular la ruta.");
            }
        }

        function calculateSelectedRoute() {
    let selectedBranchValue = document.getElementById("branchSelect").value;

    if (!selectedBranchValue) {
        alert("Por favor, selecciona una sucursal.");
        return;
    }

    try {
        let selectedBranch = JSON.parse(selectedBranchValue);
        getRouteData(selectedBranch).then(displayRoute);
    } catch (error) {
        console.error("Error al analizar el JSON:", error);
        alert("Hubo un error al procesar la selección.");
    }
        }
    </script>
</body>
</html>
