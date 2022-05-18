import { useParams } from 'react-router-dom';

export const PeopleDetailsPage = () => {
  const { id } = useParams();

  return <h1>People {id} details page</h1>;
};
