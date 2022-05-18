import type { Dispatch, FC } from 'react';

import { Box, Button, TextField } from '@mui/material';
import useAxios from 'axios-hooks';
import { useForm } from 'react-hook-form';

import type { People } from 'entities/people';
import type { Ward } from 'entities/wards';

import type { UpdateWardRequest, UpdateWardResponse } from '../model';
import { defaultValues, resolver } from '../model';

export type EditWardFormProps = {
  ward: Ward & { people: People[] };
  onEdit: Dispatch<Ward & { people: People[] }>;
};
export const EditWardForm: FC<EditWardFormProps> = ({ ward, onEdit }) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ defaultValues: defaultValues(ward), resolver: resolver(ward) });

  const [, updateWard] = useAxios<UpdateWardResponse, UpdateWardRequest>(
    {
      url: `/wards/${ward.id}`,
      method: 'PATCH',
    },
    { manual: true },
  );

  const onSubmit = (data: UpdateWardRequest) => {
    updateWard({ data }).then((response) => {
      onEdit({ ...response.data, people: ward.people });
    });
  };

  return (
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
        Submit
      </Button>
    </Box>
  );
};
