import { useState } from 'react';
import type { Dispatch, FC, MouseEventHandler } from 'react';

import type { ButtonProps } from '@mui/material';
import { Button, Dialog, DialogTitle } from '@mui/material';

import type { Ward } from 'entities/wards';

import { CreateWardForm } from './form';

export type CreateWardButtonProps = ButtonProps & {
  onCreate: Dispatch<Ward>;
};
export const CreateWardButton: FC<CreateWardButtonProps> = ({
  onClick,
  onCreate,
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

  const _onCreate: Dispatch<Ward> = (ward) => {
    setDialogVisible(false);
    onCreate(ward);
  };

  return (
    <>
      <Dialog open={dialogVisible} onClose={onClose}>
        <DialogTitle>Create Ward</DialogTitle>

        <CreateWardForm onCreate={_onCreate} />
      </Dialog>

      <Button onClick={_onClick} {...props}>
        {children}
      </Button>
    </>
  );
};
