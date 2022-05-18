import { Box, Button, TextField } from '@mui/material';
import useAxios from 'axios-hooks';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';

import { useIdentityContext } from 'entities/identity';

import type { SignInRequest, SignInResponse } from './model';
import { defaultValues, resolver } from './model';

export const SignInForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ defaultValues, resolver });

  const [{ loading }, signIn] = useAxios<SignInResponse, SignInRequest>(
    {
      url: '/identity/sign-in',
      method: 'POST',
    },
    { manual: true },
  );

  const [, setAccessToken] = useIdentityContext();

  const navigate = useNavigate();

  const onSubmit = (data: SignInRequest) => {
    signIn({ data }).then((response) => {
      setAccessToken(response.data.accessToken);

      navigate('/', { replace: true });
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
          label="Username"
          autoComplete={'username'}
          error={Boolean(errors['username'])}
          helperText={errors['username']?.message}
          {...register('username')}
        />
        <TextField
          label="Password"
          autoComplete={'current-password'}
          error={Boolean(errors['password'])}
          helperText={errors['password']?.message}
          type={'password'}
          {...register('password')}
        />

        <Button size={'large'} type={'submit'}>
          Submit
        </Button>
      </Box>
    </>
  );
};
