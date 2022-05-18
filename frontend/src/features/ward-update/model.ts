import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';

import type { People } from 'entities/people';
import type { Ward } from 'entities/wards';

export type UpdateWardRequest = {
  name: string;
  maxCount: number;
};

export type UpdateWardResponse = Ward;

export const defaultValues = (ward: Ward) => ({
  ...ward,
});

export const resolver = (ward: Ward & { people: People[] }) =>
  yupResolver(
    yup.object({
      name: yup.string().required('Ward name is required'),
      maxCount: yup
        .number()
        .min(
          Math.max(1, ward.people.length),
          `Max count can not be less than ${ward.people.length}`,
        )
        .required('Max count is required'),
    }),
  );
