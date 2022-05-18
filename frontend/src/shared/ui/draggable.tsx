import type { FC, ReactElement } from 'react';

import { Box } from '@mui/material';
import { useDrag } from 'react-dnd';

export type DraggableProps = {
  type: string;
  children: ReactElement;
  item: unknown;
};
export const Draggable: FC<DraggableProps> = ({ type, children, item }) => {
  const [{ isDragging }, drag] = useDrag(() => ({
    type,
    item,
    collect: (monitor) => ({
      isDragging: monitor.isDragging(),
    }),
  }));

  return (
    <Box
      ref={drag}
      sx={{
        opacity: isDragging ? 0.5 : 1,
        fontSize: 25,
        fontWeight: 'bold',
        cursor: 'move',
      }}
    >
      {children}
    </Box>
  );
};
