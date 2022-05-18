import { useParams } from 'react-router-dom';

export const DiagnoseDetailsPage = () => {
  const { id } = useParams();

  return <h1>Diagnose {id} details page</h1>;
};
