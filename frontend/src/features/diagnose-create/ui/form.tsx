import type { Dispatch, FC } from 'react';

import { Box, Button, TextField } from '@mui/material';
import useAxios from 'axios-hooks';
import { useForm } from 'react-hook-form';

import type { Diagnose } from 'entities/diagnosis';
import type {
  CreateDiagnoseRequest,
  CreateDiagnoseResponse,
} from 'features/diagnose-create/model';
import { defaultValues, resolver } from 'features/diagnose-create/model';

export type CreateDiagnoseFormProps = {
  onCreate: Dispatch<Diagnose>;
};
export const CreateDiagnoseForm: FC<CreateDiagnoseFormProps> = ({
  onCreate,
}) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ defaultValues, resolver });

  const [{ loading }, createDiagnose] = useAxios<
    CreateDiagnoseResponse,
    CreateDiagnoseRequest
  >(
    {
      url: '/diagnoses',
      method: 'POST',
    },
    { manual: true },
  );

  const onSubmit = (data: CreateDiagnoseRequest) => {
    createDiagnose({ data }).then((response) => {
      onCreate(response.data);
    });
  };

  return (
    <>
      {loading && <h2>Loading...</h2>}

      <Box
        component="form"
        onSubmit={handleSubmit(onSubmit)}
        sx={{
          display: 'flex',
          flexDirection: 'column',
          p: 4,

          '& .MuiTextField-root': { mb: 2 },

          '& .MuiTypography-root': { mb: 4 },
        }}
      >
        <TextField
          label="Name"
          error={Boolean(errors['name'])}
          helperText={errors['name']?.message}
          {...register('name')}
        />

        <Button size={'large'} type={'submit'}>
          Create
        </Button>
      </Box>
    </>
  );
};
