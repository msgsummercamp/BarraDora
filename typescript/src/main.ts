interface DogApiResponse {
    message: string;
    status: string;
}

const button = document.getElementById('dogButton') as HTMLButtonElement;
const image = document.getElementById('dogImage') as HTMLImageElement;
const loading = document.getElementById('loading') as HTMLDivElement;
const errorMessage = document.getElementById('errorMessage') as HTMLDivElement;
button.onclick = async () => {
    loading.style.display = 'block';
    image.style.display = 'none';
    errorMessage.style.display = 'none';

    try {
        const response = await fetch('https://dog.ceo/api/breeds/image/random');
        if (!response.ok) {
            throw new Error('Failed to fetch');
        }

        const data: DogApiResponse = await response.json();
        image.src = data.message;
        image.style.display = 'block';
    } catch (error) {
        errorMessage.textContent = 'Failed to load dog image. Please try again later.';
        errorMessage.style.display = 'block';
        image.style.display = 'none';
        console.error('Error fetching dog image:', error);
    } finally {
        loading.style.display = 'none';
    }
};
