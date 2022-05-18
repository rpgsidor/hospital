import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';

export type SignInRequest = {
  username: string;
  password: string;
};

export type SignInResponse = {
  accessToken: string;
};

export const defaultValues = {
  username: '',
  password: '',
};
export const resolver = yupResolver(
  yup.object({
    username: yup.string().required('Username is required'),
    password: yup.string().required('Password is required'),
  }),
);
