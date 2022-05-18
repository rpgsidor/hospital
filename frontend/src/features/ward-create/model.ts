import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';

import type { Ward } from 'entities/wards';

export type CreateWardRequest = {
  name: string;
  maxCount: number;
};

export type CreateWardResponse = Ward;

export const defaultValues = {
  name: '',
  maxCount: 0,
};
export const resolver = yupResolver(
  yup.object({
    name: yup.string().required('Ward name is required'),
    maxCount: yup
      .number()
      .min(1, 'Max count can not be less than 1')
      .required('Max count is required'),
  }),
);
