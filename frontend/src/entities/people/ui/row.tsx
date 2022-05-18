import type { FC, ReactNode } from 'react';

import { Person } from '@mui/icons-material';
import { Avatar, ListItem, ListItemAvatar, ListItemText } from '@mui/material';

import type { People } from '../model';

export type PeopleListItemProps = People & {
  Actions?: ReactNode;
};
export const PeopleListItem: FC<PeopleListItemProps> = ({
  id,
  lastName,
  firstName,
  fatherName,
  Actions,
}) => (
  <ListItem key={id} secondaryAction={Actions}>
    <ListItemAvatar>
      <Avatar>
        <Person />
      </Avatar>
    </ListItemAvatar>

    <ListItemText primary={`${lastName} ${firstName} ${fatherName}`} />
  </ListItem>
);
