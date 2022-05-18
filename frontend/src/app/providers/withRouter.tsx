import type { FC } from 'react';

import { BrowserRouter } from 'react-router-dom';

export const withRouter = (Component: FC) => {
  const RouterProvider = () => (
    <BrowserRouter>
      <Component />
    </BrowserRouter>
  );

  RouterProvider.displayName = 'RouterProvider';

  return RouterProvider;
};
