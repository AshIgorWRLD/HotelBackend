CREATE TYPE "public"."organisations_priority_enum" AS ENUM('3', '2', '1');
CREATE TABLE "organisations" ("id" uuid NOT NULL DEFAULT uuid_generate_v4(), "priority" "public"."organisations_priority_enum" NOT NULL DEFAULT '1', CONSTRAINT "PK_7bf54cba378d5b2f1d4c10ef4df" PRIMARY KEY ("id"));
