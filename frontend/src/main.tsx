import { createRoot } from 'react-dom/client';

import App from 'app';
import { configureAxios } from 'shared/api';

configureAxios();
const root = document.getElementById('root');

if (root) {
  createRoot(root).render(<App />);
} else {
  console.error('div#root was not found!');
}
