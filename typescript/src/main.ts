interface DogApiResponse {
    message: string;
    status: string;
}

const button = document.getElementById('dogButton') as HTMLButtonElement;
const image = document.getElementById('dogImage') as HTMLImageElement;
const loading = document.getElementById('loading') as HTMLDivElement;

button.addEventListener('click', async (): Promise<void> => {
    loading.style.display = 'block';
    image.style.display = 'none';

    try {
        const response = await fetch('https://dog.ceo/api/breeds/image/random');
        if (!response.ok) {
            throw new Error('Failed to fetch');
        }

        const data: DogApiResponse = await response.json();
        image.src = data.message;
        image.style.display = 'block';
    } catch (error) {
        console.error('Error:', error);
        alert('Could not load dog image.');
    } finally {
        loading.style.display = 'none';
    }
});
