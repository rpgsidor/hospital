import type { Dispatch, FC, MouseEventHandler } from 'react';
import { useState } from 'react';

import type { ButtonProps } from '@mui/material';
import { Button, Dialog, DialogTitle, IconButton } from '@mui/material';

import type { People } from 'entities/people';

import { EditPeopleForm } from './form';

export type EditPeopleButtonProps = ButtonProps & {
  people: People;
  onEdit: Dispatch<People>;
  iconButton?: boolean;
};
export const EditPeopleButton: FC<EditPeopleButtonProps> = ({
  onClick,
  people,
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

  const _onEdit: Dispatch<People> = (people) => {
    setDialogVisible(false);
    onEdit(people);
  };

  return (
    <>
      <Dialog open={dialogVisible} onClose={onClose}>
        <DialogTitle>Edit People</DialogTitle>

        <EditPeopleForm people={people} onEdit={_onEdit} />
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
