import type { Dispatch, FC } from 'react';

import { Box, Button, MenuItem, TextField } from '@mui/material';
import useAxios from 'axios-hooks';
import { useForm } from 'react-hook-form';

import type { Diagnose } from 'entities/diagnosis';
import type { People } from 'entities/people';

import type {
  CreatePeopleFormState,
  CreatePeopleRequest,
  CreatePeopleResponse,
} from '../model';
import { defaultValues, resolver } from '../model';

type CreatePeopleFormProps = {
  onCreate: Dispatch<People>;
  wardId: number;
};
export const CreatePeopleForm: FC<CreatePeopleFormProps> = ({
  wardId,
  onCreate,
}) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    defaultValues: { ...defaultValues, wardId: wardId },
    resolver,
  });

  const [{ response: diagnosesResponse, loading: diagnosesLoading }] = useAxios<
    Diagnose[]
  >({
    url: '/diagnoses',
    method: 'GET',
  });

  const [{ loading }, createPeople] = useAxios<
    CreatePeopleResponse,
    CreatePeopleRequest
  >(
    {
      url: '/people',
      method: 'POST',
    },
    { manual: true },
  );

  const onSubmit = (data: CreatePeopleFormState) => {
    const request = {
      ...data,
      diagnosisId: parseInt(data.diagnosisId, 10),
      wardId,
    };

    createPeople({ data: request }).then((response) => {
      onCreate(response.data);
    });
  };

  if (loading || diagnosesLoading) {
    return <h2>Loading...</h2>;
  }

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

      <TextField
        select
        fullWidth
        label="Diagnose"
        defaultValue=""
        inputProps={register('diagnosisId')}
        error={Boolean(errors.diagnosisId)}
        helperText={errors.diagnosisId?.message}
      >
        {diagnosesResponse?.data.map((diagnose) => (
          <MenuItem key={diagnose.id} value={diagnose.id}>
            {diagnose.name}
          </MenuItem>
        ))}
      </TextField>

      <Button size={'large'} type={'submit'}>
        Create
      </Button>
    </Box>
  );
};
