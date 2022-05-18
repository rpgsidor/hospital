import { Container } from '@mui/material';
import { Routes, Route, Navigate } from 'react-router-dom';

import { Header } from 'widgets/header';

import { DiagnosesPage, DiagnoseDetailsPage } from './diagnoses';
import { SignInPage } from './identity';
import { PeopleDetailsPage } from './people';
import { WardsPage } from './wards';

export const Routing = () => (
  <>
    <Header />

    <Container maxWidth="xl" sx={{ py: 2 }}>
      <Routes>
        <Route path={'identity/sign-in'} element={<SignInPage />} />

        <Route index element={<Navigate to={'wards'} />} />

        <Route path={'wards'} element={<WardsPage />} />

        <Route path={'diagnoses'} element={<DiagnosesPage />} />
        <Route path={'diagnoses/:id'} element={<DiagnoseDetailsPage />} />

        <Route path={'people/:id'} element={<PeopleDetailsPage />} />
      </Routes>
    </Container>
  </>
);
