const targetNode = document.getElementById('map_canvas').childNodes[0];

            // Create a new instance of MutationObserver
            const observer = new MutationObserver(function(mutationsList, observer) {
                for (let mutation of mutationsList) {
                    if (mutation.type === 'childList') {
                        const infomsgElement = document.querySelector('.infomsg');
                        if (infomsgElement && infomsgElement.textContent.includes("You are using a browser that is not supported by the Google Maps JavaScript API")) {
                            infomsgElement.textContent = "You are using a browser that is not supported by the Google Maps API. Please consider changing your browser.";
                        }
                    }
                }
            });

            // Configure and start observing the target node
            //const config = { childList: true, subtree: true };
            observer.observe(targetNode, domTre);