ALTER TABLE "sync_job"
    ALTER COLUMN "index_info_id" DROP NOT NULL,
    ADD COLUMN "sync_execution_id" UUID NULL,
    ADD COLUMN "index_classification_snapshot" VARCHAR(30) NULL ,
    ADD COLUMN "index_name_snapshot" VARCHAR(100) NULL;

ALTER TABLE sync_job
DROP CONSTRAINT "FK_index_info_TO_sync_job_1";

ALTER TABLE sync_job
    ADD CONSTRAINT "FK_index_info_TO_sync_job_1"
        FOREIGN KEY (index_info_id)
            REFERENCES index_info (id)
            ON DELETE SET NULL;