ALTER TABLE "hotels" ADD "name" character varying(255) NOT NULL;
ALTER TABLE "hotels" RENAME COLUMN "roomsForFloor" TO "description";
ALTER TABLE "hotels" DROP COLUMN "description";
ALTER TABLE "hotels" ADD "description" text NOT NULL;
