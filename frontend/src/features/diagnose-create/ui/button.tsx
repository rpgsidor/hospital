import { useState } from 'react';
import type { FC, MouseEventHandler, Dispatch } from 'react';

import type { ButtonProps } from '@mui/material';
import { Button, Dialog, DialogTitle } from '@mui/material';

import type { Diagnose } from 'entities/diagnosis';

import { CreateDiagnoseForm } from './form';

export type CreateDiagnoseButtonProps = ButtonProps & {
  onCreate: Dispatch<Diagnose>;
};
export const CreateDiagnoseButton: FC<CreateDiagnoseButtonProps> = ({
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

  const _onCreate: Dispatch<Diagnose> = (diagnose) => {
    setDialogVisible(false);
    onCreate(diagnose);
  };

  return (
    <>
      <Dialog open={dialogVisible} onClose={onClose}>
        <DialogTitle>Create Diagnose</DialogTitle>

        <CreateDiagnoseForm onCreate={_onCreate} />
      </Dialog>

      <Button onClick={_onClick} {...props}>
        {children}
      </Button>
    </>
  );
};
