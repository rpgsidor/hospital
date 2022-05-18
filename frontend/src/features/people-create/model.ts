import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';

import type { People } from 'entities/people';

export type CreatePeopleRequest = {
  firstName: string;
  lastName: string;
  fatherName: string;
  diagnosisId: number;
  wardId: number;
};

export type CreatePeopleFormState = Omit<CreatePeopleRequest, 'diagnosisId'> & {
  diagnosisId: string;
};

export type CreatePeopleResponse = People;

export const defaultValues = {
  firstName: '',
  lastName: '',
  fatherName: '',
  diagnosisId: '',
  wardId: undefined,
};
export const resolver = yupResolver(
  yup.object({
    firstName: yup.string().required('First name is required'),
    lastName: yup.string().required('Last name is required'),
    fatherName: yup.string().required('Father name is required'),
    diagnosisId: yup.string().required('Diagnose is required'),
    wardId: yup.string().required('Ward is required'),
  }),
);
