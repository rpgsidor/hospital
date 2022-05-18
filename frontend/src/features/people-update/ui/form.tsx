import type { Dispatch, FC } from 'react';

import { Box, Button, MenuItem, TextField } from '@mui/material';
import useAxios from 'axios-hooks';
import { useForm, Controller } from 'react-hook-form';

import type { Diagnose } from 'entities/diagnosis';
import type { People } from 'entities/people';

import type {
  UpdatePeopleFormState,
  UpdatePeopleRequest,
  UpdatePeopleResponse,
} from '../model';
import { defaultValues as defaultValuesFn, resolver } from '../model';

export type EditPeopleFormProps = {
  people: People;
  onEdit: Dispatch<People>;
};
export const EditPeopleForm: FC<EditPeopleFormProps> = ({ people, onEdit }) => {
  const defaultValues = defaultValuesFn(people);

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
  } = useForm({ defaultValues, resolver });

  console.log(defaultValues);

  const [{ response: diagnosesResponse, loading: diagnosesLoading }] = useAxios<
    Diagnose[]
  >({
    url: '/diagnoses',
    method: 'GET',
  });

  const [, updatePeople] = useAxios<UpdatePeopleResponse, UpdatePeopleRequest>(
    {
      url: `/people/${people.id}`,
      method: 'PATCH',
    },
    { manual: true },
  );

  const onSubmit = (data: UpdatePeopleFormState) => {
    updatePeople({
      data: { ...data, diagnosisId: parseInt(data.diagnosisId, 10) },
    }).then((response) => {
      onEdit(response.data);
    });
  };

  if (diagnosesLoading) {
    return <h2>Loading...</h2>;
  }

  console.log(diagnosesResponse);

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
        label="Last name"
        error={Boolean(errors['lastName'])}
        helperText={errors['lastName']?.message}
        {...register('lastName')}
      />

      <TextField
        label="First name"
        error={Boolean(errors['firstName'])}
        helperText={errors['firstName']?.message}
        {...register('firstName')}
      />

      <TextField
        label="Father name"
        error={Boolean(errors['fatherName'])}
        helperText={errors['fatherName']?.message}
        {...register('fatherName')}
      />

      <Controller
        control={control}
        name="diagnosisId"
        render={({ field, fieldState: { error } }) => (
          <TextField
            select
            fullWidth
            {...field}
            label="Diagnose"
            error={Boolean(error)}
            helperText={error?.message}
          >
            {diagnosesResponse?.data.map((diagnose) => (
              <MenuItem key={diagnose.id} value={diagnose.id.toString(10)}>
                {diagnose.name}
              </MenuItem>
            ))}
          </TextField>
        )}
      />

      <Button size={'large'} type={'submit'}>
        Submit
      </Button>
    </Box>
  );
};
