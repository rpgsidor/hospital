import { yupResolver } from '@hookform/resolvers/yup';
import axios from 'axios';
import * as yup from 'yup';

import type { People } from 'entities/people';

export type UpdatePeopleRequest = {
  firstName: string;
  lastName: string;
  fatherName: string;
  diagnosisId: number;
  wardId: number;
};

export type UpdatePeopleFormState = Omit<UpdatePeopleRequest, 'diagnosisId'> & {
  diagnosisId: string;
};

export type UpdatePeopleResponse = People;

export const defaultValues = (people: People) => ({
  ...people,
  diagnosisId: people.diagnosisId.toString(10),
});
export const resolver = yupResolver(
  yup.object({
    firstName: yup.string().required('First name is required'),
    lastName: yup.string().required('Last name is required'),
    fatherName: yup.string().required('Father name is required'),
    diagnosisId: yup.string().required('Diagnose is required'),
    wardId: yup.string().required('Ward is required'),
  }),
);

export const changeWard = (people: People, newWardId: number) =>
  axios
    .patch<UpdatePeopleResponse>(`/people/${people.id}`, {
      ...people,
      wardId: newWardId,
    })
    .then();
