import type { FC } from 'react';
import { StrictMode } from 'react';

export const withStrictMode = (Component: FC) => {
  const StrictModeProvider = () => (
    <StrictMode>
      <Component />
    </StrictMode>
  );

  StrictModeProvider.displayName = 'StrictModeProvider';

  return StrictModeProvider;
};
