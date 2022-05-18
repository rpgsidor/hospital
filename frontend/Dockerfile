FROM node:current-alpine as build

WORKDIR /frontend
COPY ./package.json ./
COPY ./yarn.lock ./

RUN yarn

COPY ./ ./

RUN yarn build

FROM node:current-alpine
COPY --from=build /frontend/dist /dist

RUN npm i -g serve

CMD ["serve", "-l", "3000", "-s", "dist"]
