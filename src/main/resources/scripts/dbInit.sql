BEGIN TRANSACTION;
DROP TABLE IF EXISTS "Image";
CREATE TABLE IF NOT EXISTS "Image"
(
    "imageId"        INTEGER NOT NULL,
    "imagePath"      TEXT    NOT NULL,
    "imageFilename"  TEXT    NOT NULL,
    "imageExtension" TEXT    NOT NULL,
    UNIQUE ("imageId"),
    CONSTRAINT "no_duplicate_files" UNIQUE ("imageFilename", "imageExtension"),
    PRIMARY KEY ("imageId" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "Tag";
CREATE TABLE IF NOT EXISTS "Tag"
(
    "tagId"   INTEGER NOT NULL,
    "tagName" TEXT    NOT NULL UNIQUE,
    CONSTRAINT "no_duplicateNames" UNIQUE ("tagName"),
    PRIMARY KEY ("tagId" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "ImageTag";
CREATE TABLE IF NOT EXISTS "ImageTag"
(
    "itImageId" INTEGER NOT NULL,
    "itTagId"   INTEGER NOT NULL,
    "itValue"   TEXT    NOT NULL,
    CONSTRAINT "fkTag" FOREIGN KEY ("itTagId") REFERENCES "Tag" ("tagId"),
    CONSTRAINT "fkImage" FOREIGN KEY ("itImageId") REFERENCES "Image" ("imageId"),
    PRIMARY KEY ("itImageId", "itTagId", "itValue")
);
INSERT INTO "Image"
VALUES (1, 'C:\Users\Geo\OneDrive\IdeaProjects\tagimage\src\main\resources\img', 'testImage',
        'jpg');
INSERT INTO "Tag"
VALUES (1, 'Food');
INSERT INTO "Tag"
VALUES (2, 'Drink');
INSERT INTO "ImageTag"
VALUES (1, 1, 'Nuts');
INSERT INTO "ImageTag"
VALUES (1, 2, 'Cocktail');
INSERT INTO "Image"
VALUES (2, 'C:\Users\Geo\OneDrive\IdeaProjects\tagimage\src\main\resources\img', 'erd', 'jpg');
INSERT INTO "Tag"
VALUES (3, 'Table');
INSERT INTO "ImageTag"
VALUES (2, 3, 'ImageTag');
INSERT INTO "ImageTag"
VALUES (2, 3, 'Image');
INSERT INTO "ImageTag"
VALUES (2, 3, 'Tag');
DROP VIEW IF EXISTS "tagged_images_view";
CREATE VIEW tagged_images_view AS
SELECT Image.imageId, Tag.tagName, ImageTag.itValue
FROM Image
         INNER JOIN ImageTag ON Image.imageId = ImageTag.itImageId
         INNER JOIN Tag ON ImageTag.itTagId = Tag.tagId;
DROP VIEW IF EXISTS "record_view";
CREATE VIEW record_view AS
SELECT Image.imageId,
       Image.imagePath,
       Image.imageFilename,
       Image.imageExtension,
       tagId,
       Tag.tagName,
       ImageTag.itValue
FROM Image
         INNER JOIN ImageTag ON Image.imageId = ImageTag.itImageId
         INNER JOIN Tag ON ImageTag.itTagId = Tag.tagId;
COMMIT;
