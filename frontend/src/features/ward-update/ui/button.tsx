import { useState } from 'react';
import type { Dispatch, FC, MouseEventHandler } from 'react';

import { Button, Dialog, DialogTitle, IconButton } from '@mui/material';
import type { ButtonProps } from '@mui/material';

import type { People } from 'entities/people';
import type { Ward } from 'entities/wards';

import { EditWardForm } from './form';

export type EditWardButtonProps = ButtonProps & {
  ward: Ward & { people: People[] };
  onEdit: Dispatch<Ward & { people: People[] }>;
  iconButton?: boolean;
};
export const EditWardButton: FC<EditWardButtonProps> = ({
  onClick,
  ward,
  onEdit,
  iconButton,
  children,
  ...props
}) => {
  const [dialogVisible, setDialogVisible] = useState(false);

  const onClose = () => {
    setDialogVisible(false);
  };

  const _onClick: MouseEventHandler<HTMLButtonElement> = (e) => {
    setDialogVisible(true);

    if (onClick) {
      onClick(e);
    }
  };

  const _onEdit: Dispatch<Ward & { people: People[] }> = (ward) => {
    setDialogVisible(false);
    onEdit(ward);
  };

  return (
    <>
      <Dialog open={dialogVisible} onClose={onClose}>
        <DialogTitle>Edit Ward</DialogTitle>

        <EditWardForm ward={ward} onEdit={_onEdit} />
      </Dialog>

      {iconButton ? (
        <IconButton onClick={_onClick} {...props}>
          {children}
        </IconButton>
      ) : (
        <Button onClick={_onClick} {...props}>
          {children}
        </Button>
      )}
    </>
  );
};
