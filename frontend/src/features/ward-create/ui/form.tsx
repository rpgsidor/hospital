import type { Dispatch, FC } from 'react';

import { Box, Button, TextField } from '@mui/material';
import useAxios from 'axios-hooks';
import { useForm } from 'react-hook-form';

import type { Ward } from 'entities/wards';

import type { CreateWardRequest, CreateWardResponse } from '../model';
import { defaultValues, resolver } from '../model';

export type CreateWardFormProps = {
  onCreate: Dispatch<Ward>;
};
export const CreateWardForm: FC<CreateWardFormProps> = ({ onCreate }) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ defaultValues, resolver });

  const [{ loading }, createWard] = useAxios<
    CreateWardResponse,
    CreateWardRequest
  >(
    {
      url: '/wards',
      method: 'POST',
    },
    { manual: true },
  );

  const onSubmit = (data: CreateWardRequest) => {
    createWard({ data }).then((response) => {
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

        <TextField
          label="Max count"
          error={Boolean(errors['maxCount'])}
          helperText={errors['maxCount']?.message}
          type={'number'}
          {...register('maxCount')}
        />

        <Button size={'large'} type={'submit'}>
          Create
        </Button>
      </Box>
    </>
  );
};
