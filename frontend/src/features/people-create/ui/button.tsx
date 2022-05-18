import { useState } from 'react';
import type { Dispatch, FC, MouseEventHandler } from 'react';

import type { ButtonProps } from '@mui/material';
import { Button, Dialog, DialogTitle } from '@mui/material';

import type { People } from 'entities/people';

import { CreatePeopleForm } from './form';

export type CreatPeopleButtonProps = ButtonProps & {
  wardId: number;
  onCreate: Dispatch<People>;
};
export const CreatePeopleButton: FC<CreatPeopleButtonProps> = ({
  onClick,
  onCreate,
  wardId,
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

  const _onCreate: Dispatch<People> = (people) => {
    setDialogVisible(false);
    onCreate(people);
  };

  return (
    <>
      <Dialog open={dialogVisible} onClose={onClose}>
        <DialogTitle>Add People</DialogTitle>

        <CreatePeopleForm wardId={wardId} onCreate={_onCreate} />
      </Dialog>

      <Button onClick={_onClick} {...props}>
        {children}
      </Button>
    </>
  );
};
