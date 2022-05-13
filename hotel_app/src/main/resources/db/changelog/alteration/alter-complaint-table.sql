ALTER TABLE "complaints" ADD CONSTRAINT "FK_4b5fb19c320cd50b6e4faf998a9" FOREIGN KEY ("userId") REFERENCES "users"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "complaints" ADD "title" character varying(255) NOT NULL;
