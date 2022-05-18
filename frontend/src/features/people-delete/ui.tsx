import type { Dispatch, FC, MouseEventHandler } from 'react';

import { Button, IconButton } from '@mui/material';
import type { ButtonProps } from '@mui/material';
import useAxios from 'axios-hooks';

export const DeletePeopleButton: FC<
  ButtonProps & {
    iconButton?: boolean;
    peopleId: number;
    onDelete: Dispatch<number>;
  }
> = ({ peopleId, onDelete, onClick, children, iconButton, ...props }) => {
  const [, deletePeople] = useAxios(
    {
      url: `/people/${peopleId}`,
      method: 'DELETE',
    },
    { manual: true },
  );
  const _onClick: MouseEventHandler<HTMLButtonElement> = (e) => {
    deletePeople().then(() => {
      onDelete(peopleId);
    });

    if (onClick) {
      onClick(e);
    }
  };

  return iconButton ? (
    <IconButton onClick={_onClick} {...props}>
      {children}
    </IconButton>
  ) : (
    <Button onClick={_onClick} {...props}>
      {children}
    </Button>
  );
};
