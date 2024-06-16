//diese Datei macht die Karte aus den Koordinaten und macht mit Puppeteer ein Bild davon
const puppeteer = require('puppeteer');
const fs = require('fs');

const coordinatesFilePath = process.argv[2];
const filePath = process.argv[3];

(async () => {
    const coordinates = JSON.parse(fs.readFileSync(coordinatesFilePath, 'utf8'));

    const browser = await puppeteer.launch();
    const page = await browser.newPage();

    const content = `
        <!DOCTYPE html>
        <html>
        <head>
            <title>Map</title>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
            <style>
                #map { height: 349px; width: 784px; }
                body, html { margin: 0; padding: 0; }
            </style>
        </head>
        <body>
            <div id="map"></div>
            <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
            <script>
                const coordinates = ${JSON.stringify(coordinates)};

                // Correct the order if necessary, assuming your original data is [latitude, longitude]
                const correctedCoordinates = coordinates.map(coord => [coord[1], coord[0]]);

                const map = L.map('map').setView(correctedCoordinates[0], 13);
                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    attribution: '© OpenStreetMap contributors'
                }).addTo(map);

                const polyline = L.polyline(correctedCoordinates, { color: 'blue' }).addTo(map);

                map.fitBounds(polyline.getBounds());
            </script>
        </body>
        </html>
    `;

    await page.setContent(content);
    await page.waitForSelector('#map');
    await page.setViewport({ width: 784, height: 349 });
    await page.screenshot({ path: filePath });

    await browser.close();
})();
