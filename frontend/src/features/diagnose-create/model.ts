import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';

import type { Diagnose } from 'entities/diagnosis';

export type CreateDiagnoseRequest = {
  name: string;
};

export type CreateDiagnoseResponse = Diagnose;

export const defaultValues = {
  name: '',
};
export const resolver = yupResolver(
  yup.object({
    name: yup.string().required('Diagnose name is required'),
  }),
);
