import { useEffect, useState } from 'react';

import { Add, DeleteOutlined, EditOutlined } from '@mui/icons-material';
import { Stack } from '@mui/material';
import axios from 'axios';
import useAxios from 'axios-hooks';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';

import { useIdentityContext } from 'entities/identity';
import type { People } from 'entities/people';
import { PeopleListItem } from 'entities/people';
import type { Ward } from 'entities/wards';
import { WardAccordion } from 'entities/wards';
import { CreatePeopleButton } from 'features/people-create';
import { DeletePeopleButton } from 'features/people-delete';
import { changeWard, EditPeopleButton } from 'features/people-update';
import { CreateWardButton } from 'features/ward-create';
import { DeleteWardButton } from 'features/ward-delete';
import { EditWardButton } from 'features/ward-update';
import { Draggable } from 'shared/ui/draggable';
import { Droppable } from 'shared/ui/droppable';

export const WardsPage = () => {
  const [accessToken] = useIdentityContext();

  const [, getWards] = useAxios<Ward[]>('/wards');

  const [loading, setLoading] = useState(true);
  const [wards, setWards] = useState<(Ward & { people: People[] })[]>([]);

  useEffect(() => {
    getWards().then(async (response) => {
      const wardsWithPeople = await Promise.all(
        response.data.map(async (ward) => {
          const peopleResponse = await axios.get<People[]>(
            `/wards/${ward.id}/people`,
          );

          return { ...ward, people: peopleResponse.data };
        }),
      );

      setWards(wardsWithPeople);
      setLoading(false);
    });
  }, []);

  const onCreateWard = (ward: Ward) => {
    setWards((prevWards) => [...prevWards, { ...ward, people: [] }]);
  };

  const onEditWard = (newWard: Ward & { people: People[] }) => {
    setWards((prevWards) =>
      prevWards.map((ward) => {
        if (ward.id === newWard.id) {
          return newWard;
        }

        return ward;
      }),
    );
  };

  const onDeleteWard = (wardId: number) => {
    setWards((prevWards) => prevWards.filter((ward) => ward.id !== wardId));
  };

  const onAddPeople = (people: People) => {
    setWards((prevWards) =>
      prevWards.map((ward) => {
        if (ward.id === people.wardId) {
          ward.people.push(people);
        }

        return ward;
      }),
    );
  };

  const onEditPeople = (people: People) => {
    setWards((prevWards) =>
      prevWards.map((ward) => {
        if (ward.id === people.wardId) {
          ward.people = ward.people.map((wardPeople) => {
            if (wardPeople.id === people.id) {
              return people;
            }
            return wardPeople;
          });
        }

        return ward;
      }),
    );
  };

  const onDeletePeople = (peopleId: number) => {
    setWards((prevWards) =>
      prevWards.map((ward) => ({
        ...ward,
        people: ward.people.filter((people) => people.id !== peopleId),
      })),
    );
  };

  const onDropPeople = (newWardId: number) => (people: People) => {
    changeWard(people, newWardId).then(() => {
      setWards((prevWards) =>
        prevWards.map((ward) => {
          if (ward.id === people.wardId) {
            ward.people = ward.people.filter(
              (wardPeople) => wardPeople.id !== people.id,
            );
          }
          if (ward.id === newWardId) {
            ward.people.push({ ...people, wardId: newWardId });
          }

          return ward;
        }),
      );
    });
  };

  return (
    <>
      <Stack
        direction={'row'}
        justifyContent={'space-between'}
        alignItems={'center'}
      >
        <h1>Wards Page</h1>

        {accessToken && (
          <CreateWardButton
            onCreate={onCreateWard}
            variant="outlined"
            startIcon={<Add />}
          >
            Create
          </CreateWardButton>
        )}
      </Stack>

      {loading && <h2>Loading...</h2>}

      <DndProvider backend={HTML5Backend}>
        {wards?.map((ward) => (
          <Droppable
            key={ward.id}
            type={'people'}
            drop={onDropPeople(ward.id)}
            canDrop={() => !!accessToken && ward.people.length < ward.maxCount}
          >
            <WardAccordion
              {...ward}
              canEdit={accessToken !== undefined}
              peopleAmount={ward.people.length}
              PeopleList={
                <>
                  {ward.people.map((people) => (
                    <Draggable key={people.id} type={'people'} item={people}>
                      <PeopleListItem
                        key={people.id}
                        {...people}
                        Actions={
                          accessToken && (
                            <>
                              <EditPeopleButton
                                iconButton
                                color={'primary'}
                                people={people}
                                onEdit={onEditPeople}
                              >
                                <EditOutlined />
                              </EditPeopleButton>
                              <DeletePeopleButton
                                peopleId={people.id}
                                onDelete={onDeletePeople}
                                iconButton
                                color={'error'}
                              >
                                <DeleteOutlined />
                              </DeletePeopleButton>
                            </>
                          )
                        }
                      />
                    </Draggable>
                  ))}
                </>
              }
              WardActions={
                <Stack
                  direction={'row'}
                  justifyContent={'space-between'}
                  sx={{ flexGrow: 1 }}
                >
                  <CreatePeopleButton
                    wardId={ward.id}
                    onCreate={onAddPeople}
                    disabled={ward.people.length >= ward.maxCount}
                  >
                    Add People
                  </CreatePeopleButton>

                  <Stack direction={'row'}>
                    <EditWardButton ward={ward} onEdit={onEditWard}>
                      Edit
                    </EditWardButton>
                    <DeleteWardButton
                      wardId={ward.id}
                      disabled={ward.people.length > 0}
                      onDelete={onDeleteWard}
                      color={'error'}
                    >
                      Delete
                    </DeleteWardButton>
                  </Stack>
                </Stack>
              }
            />
          </Droppable>
        ))}
      </DndProvider>
    </>
  );
};
