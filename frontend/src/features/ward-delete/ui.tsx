import type { Dispatch, FC, MouseEventHandler } from 'react';

import type { ButtonProps } from '@mui/material';
import { Button, IconButton } from '@mui/material';
import useAxios from 'axios-hooks';

export const DeleteWardButton: FC<
  ButtonProps & {
    iconButton?: boolean;
    wardId: number;
    onDelete: Dispatch<number>;
  }
> = ({ wardId, onDelete, onClick, children, iconButton, ...props }) => {
  const [, deleteWard] = useAxios(
    {
      url: `/wards/${wardId}`,
      method: 'DELETE',
    },
    { manual: true },
  );
  const _onClick: MouseEventHandler<HTMLButtonElement> = (e) => {
    deleteWard().then(() => {
      onDelete(wardId);
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
