import { Add } from '@mui/icons-material';
import { Stack, Typography } from '@mui/material';
import useAxios from 'axios-hooks';

import type { Diagnose } from 'entities/diagnosis';
import { useIdentityContext } from 'entities/identity';
import { CreateDiagnoseButton } from 'features/diagnose-create';

export const DiagnosesPage = () => {
  const [{ data, loading }, fetch] = useAxios<Diagnose[]>('/diagnoses');
  const [accessToken] = useIdentityContext();

  const onCreate = () => {
    fetch();
  };

  return (
    <>
      <Stack
        direction={'row'}
        justifyContent={'space-between'}
        alignItems={'center'}
      >
        <h1>Diagnoses Page</h1>

        {accessToken && (
          <CreateDiagnoseButton
            onCreate={onCreate}
            startIcon={<Add />}
            variant="outlined"
          >
            Create
          </CreateDiagnoseButton>
        )}
      </Stack>

      {loading && <h2>Loading...</h2>}

      <Stack spacing={2}>
        {data?.map((diagnose) => (
          <Typography key={diagnose.id} variant={'h6'} component={'h2'}>
            {diagnose.name}
          </Typography>
        ))}
      </Stack>
    </>
  );
};

export { DiagnoseDetailsPage } from './_id';
