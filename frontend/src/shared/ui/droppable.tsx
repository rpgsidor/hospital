import type { ReactElement } from 'react';
import { cloneElement } from 'react';

import type { DropTargetMonitor } from 'react-dnd';
import { useDrop } from 'react-dnd';

export type DroppableProps<T> = {
  type: string;
  canDrop?: () => boolean;
  drop?: (item: T, monitor: DropTargetMonitor<T, void>) => void;
  children: ReactElement;
};
export const Droppable = <T extends object>({
  type,
  canDrop,
  drop,
  children,
}: DroppableProps<T>) => {
  const [{}, dropRef] = useDrop(
    () => ({
      accept: type,
      canDrop,
      drop,
      collect: (monitor) => ({
        canDrop: monitor.canDrop(),
      }),
    }),
    [],
  );
  return cloneElement(children, { ref: dropRef });
};
