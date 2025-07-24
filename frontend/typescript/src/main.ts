interface DogApiResponse {
    message: string;
    status: string;
}

function fetchDogImage(): void {
    const loading = document.getElementById('loading') as HTMLDivElement;
    const image = document.getElementById('dogImage') as HTMLImageElement;
    const errorMessage = document.getElementById('errorMessage') as HTMLDivElement;

    loading.style.display = 'block';
    image.style.display = 'none';
    errorMessage.style.display = 'none';

    fetch('https://dog.ceo/api/breeds/image/random')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch');
            }
            return response.json();
        })
        .then((data: DogApiResponse) => {
            image.src = data.message;
            image.style.display = 'block';
        })
        .catch(error => {
            errorMessage.textContent = 'Failed to load dog image. Please try again later.';
            errorMessage.style.display = 'block';
            image.style.display = 'none';
            console.error('Error fetching dog image:', error);
        })
        .then(() => {
            loading.style.display = 'none';
        });
}

(window as any).fetchDogImage = fetchDogImage;