ALTER TABLE "apartments" ADD CONSTRAINT "FK_fd7e52cafa261ddaaa15cfe5ae3" FOREIGN KEY ("hotelId") REFERENCES "hotels"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "apartments" ADD "name" character varying(255) NOT NULL;
ALTER TABLE "apartments" ADD "description" text NOT NULL;
ALTER TABLE "apartments" ADD "availableCount" integer NOT NULL;
