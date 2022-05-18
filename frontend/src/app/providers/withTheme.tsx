import type { FC } from 'react';

import { CssBaseline, ThemeProvider } from '@mui/material';

import { defaultTheme } from 'shared/config/theme';

export const withTheme = (Component: FC) => {
  const _ThemeProvider = () => (
    <ThemeProvider theme={defaultTheme}>
      <CssBaseline />

      <Component />
    </ThemeProvider>
  );

  _ThemeProvider.displayName = 'StrictModeProvider';

  return _ThemeProvider;
};
