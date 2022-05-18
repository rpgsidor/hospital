import type { MouseEventHandler, ReactElement } from 'react';
import { forwardRef, useState } from 'react';

import {
  Accordion,
  AccordionActions,
  AccordionDetails,
  AccordionSummary,
  Divider,
  List,
  Stack,
  Typography,
} from '@mui/material';

import type { Ward } from '../model';

export type WardAccordionProps = Ward & {
  canEdit: boolean;

  peopleAmount?: number;
  PeopleList?: ReactElement;

  WardActions: ReactElement;
};
export const WardAccordion = forwardRef<HTMLDivElement, WardAccordionProps>(
  // False positive rule
  // eslint-disable-next-line react/prop-types
  ({ name, maxCount, canEdit, peopleAmount, PeopleList, WardActions }, ref) => {
    const [expanded, setExpanded] = useState(false);

    const onClick: MouseEventHandler = () => {
      setExpanded((prev) => !prev);
    };

    return (
      <Accordion ref={ref} expanded={expanded}>
        <AccordionSummary
          onClick={onClick}
          sx={{
            '& .MuiAccordionSummary-content': {
              flexDirection: 'column',
            },
          }}
        >
          <Stack
            direction={'row'}
            justifyContent={'space-between'}
            alignItems={'center'}
          >
            <Typography
              variant={'h6'}
              component={'span'}
              sx={{ textDecoration: 'none' }}
            >
              {name}
            </Typography>

            <Typography
              color={'grey.700'}
              variant={'subtitle2'}
              component={'span'}
            >
              People {peopleAmount}/{maxCount}
            </Typography>
          </Stack>
        </AccordionSummary>
        <Divider />

        <AccordionDetails>
          <List>{PeopleList}</List>
        </AccordionDetails>

        {canEdit && WardActions && (
          <>
            <Divider />
            <AccordionActions sx={{ justifyContent: 'stretch' }}>
              {WardActions}
            </AccordionActions>
          </>
        )}
      </Accordion>
    );
  },
);

WardAccordion.displayName = 'WardAccordion';
