FROM postgres

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB faq_db

ENTRYPOINT [ "/database/scripts/init.sql" ]

EXPOSE 5432
