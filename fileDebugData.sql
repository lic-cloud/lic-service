
insert into file_mapping
(create_at, update_at, delete_at,
 file_name, info_id, is_dir,
 owner_id, pid, share, size, status)
values(
          now(), now(), null,
          'debug_file_mapping', 1, false,
          1, 0, false, 3321.1, 1
      );

insert into file_mapping
(id, create_at, update_at, delete_at,
 file_name, info_id, is_dir,
 owner_id, pid, share, size, status)
values(
          1, now(), now(), null,
          'root', 0, true,
          1, 0, false, 0, 1
      );

insert into file_info(
    create_at, update_at, ext_config,
    file_name, md5, namespace, owner,
    path, reference, size)
VALUES (now(), now(), null, 'file','md5', 0, 'debug_file_info', '/file', 1, 233.3);

