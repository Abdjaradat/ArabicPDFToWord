package com.arabicpdftoword.app.core.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.arabicpdftoword.app.core.database.entity.ConversionEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ConversionDao_Impl implements ConversionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ConversionEntity> __insertionAdapterOfConversionEntity;

  private final EntityDeletionOrUpdateAdapter<ConversionEntity> __deletionAdapterOfConversionEntity;

  private final EntityDeletionOrUpdateAdapter<ConversionEntity> __updateAdapterOfConversionEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByConversionId;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStatus;

  private final SharedSQLiteStatement __preparedStmtOfMarkCompleted;

  public ConversionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfConversionEntity = new EntityInsertionAdapter<ConversionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `conversions` (`id`,`conversion_id`,`original_file_name`,`original_file_size`,`output_file_name`,`output_file_size`,`status`,`page_count`,`ocr_used`,`error_message`,`language`,`created_at`,`completed_at`,`file_path`,`output_path`,`is_premium`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ConversionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getConversionId());
        statement.bindString(3, entity.getOriginalFileName());
        statement.bindLong(4, entity.getOriginalFileSize());
        if (entity.getOutputFileName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getOutputFileName());
        }
        if (entity.getOutputFileSize() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getOutputFileSize());
        }
        statement.bindString(7, entity.getStatus());
        if (entity.getPageCount() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getPageCount());
        }
        final int _tmp = entity.getOcrUsed() ? 1 : 0;
        statement.bindLong(9, _tmp);
        if (entity.getErrorMessage() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getErrorMessage());
        }
        if (entity.getLanguage() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getLanguage());
        }
        statement.bindLong(12, entity.getCreatedAt());
        if (entity.getCompletedAt() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getCompletedAt());
        }
        if (entity.getFilePath() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getFilePath());
        }
        if (entity.getOutputPath() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getOutputPath());
        }
        final int _tmp_1 = entity.isPremium() ? 1 : 0;
        statement.bindLong(16, _tmp_1);
      }
    };
    this.__deletionAdapterOfConversionEntity = new EntityDeletionOrUpdateAdapter<ConversionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `conversions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ConversionEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfConversionEntity = new EntityDeletionOrUpdateAdapter<ConversionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `conversions` SET `id` = ?,`conversion_id` = ?,`original_file_name` = ?,`original_file_size` = ?,`output_file_name` = ?,`output_file_size` = ?,`status` = ?,`page_count` = ?,`ocr_used` = ?,`error_message` = ?,`language` = ?,`created_at` = ?,`completed_at` = ?,`file_path` = ?,`output_path` = ?,`is_premium` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ConversionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getConversionId());
        statement.bindString(3, entity.getOriginalFileName());
        statement.bindLong(4, entity.getOriginalFileSize());
        if (entity.getOutputFileName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getOutputFileName());
        }
        if (entity.getOutputFileSize() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getOutputFileSize());
        }
        statement.bindString(7, entity.getStatus());
        if (entity.getPageCount() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getPageCount());
        }
        final int _tmp = entity.getOcrUsed() ? 1 : 0;
        statement.bindLong(9, _tmp);
        if (entity.getErrorMessage() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getErrorMessage());
        }
        if (entity.getLanguage() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getLanguage());
        }
        statement.bindLong(12, entity.getCreatedAt());
        if (entity.getCompletedAt() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getCompletedAt());
        }
        if (entity.getFilePath() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getFilePath());
        }
        if (entity.getOutputPath() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getOutputPath());
        }
        final int _tmp_1 = entity.isPremium() ? 1 : 0;
        statement.bindLong(16, _tmp_1);
        statement.bindLong(17, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteByConversionId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM conversions WHERE conversion_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM conversions";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE conversions SET status = ?, error_message = ?, completed_at = ? WHERE conversion_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkCompleted = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE conversions SET output_file_name = ?, output_file_size = ?, output_path = ?, status = 'completed', completed_at = ? WHERE conversion_id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ConversionEntity entity,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfConversionEntity.insertAndReturnId(entity);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final ConversionEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfConversionEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final ConversionEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfConversionEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByConversionId(final String conversionId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByConversionId.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, conversionId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteByConversionId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStatus(final String conversionId, final String status,
      final String errorMessage, final Long completedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStatus.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, status);
        _argIndex = 2;
        if (errorMessage == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, errorMessage);
        }
        _argIndex = 3;
        if (completedAt == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, completedAt);
        }
        _argIndex = 4;
        _stmt.bindString(_argIndex, conversionId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markCompleted(final String conversionId, final String fileName, final Long fileSize,
      final String filePath, final long completedAt, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkCompleted.acquire();
        int _argIndex = 1;
        if (fileName == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, fileName);
        }
        _argIndex = 2;
        if (fileSize == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, fileSize);
        }
        _argIndex = 3;
        if (filePath == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, filePath);
        }
        _argIndex = 4;
        _stmt.bindLong(_argIndex, completedAt);
        _argIndex = 5;
        _stmt.bindString(_argIndex, conversionId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkCompleted.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ConversionEntity>> getAllConversions() {
    final String _sql = "SELECT * FROM conversions ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"conversions"}, new Callable<List<ConversionEntity>>() {
      @Override
      @NonNull
      public List<ConversionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfConversionId = CursorUtil.getColumnIndexOrThrow(_cursor, "conversion_id");
          final int _cursorIndexOfOriginalFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "original_file_name");
          final int _cursorIndexOfOriginalFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "original_file_size");
          final int _cursorIndexOfOutputFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "output_file_name");
          final int _cursorIndexOfOutputFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "output_file_size");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfPageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "page_count");
          final int _cursorIndexOfOcrUsed = CursorUtil.getColumnIndexOrThrow(_cursor, "ocr_used");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "error_message");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completed_at");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "file_path");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "output_path");
          final int _cursorIndexOfIsPremium = CursorUtil.getColumnIndexOrThrow(_cursor, "is_premium");
          final List<ConversionEntity> _result = new ArrayList<ConversionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ConversionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpConversionId;
            _tmpConversionId = _cursor.getString(_cursorIndexOfConversionId);
            final String _tmpOriginalFileName;
            _tmpOriginalFileName = _cursor.getString(_cursorIndexOfOriginalFileName);
            final long _tmpOriginalFileSize;
            _tmpOriginalFileSize = _cursor.getLong(_cursorIndexOfOriginalFileSize);
            final String _tmpOutputFileName;
            if (_cursor.isNull(_cursorIndexOfOutputFileName)) {
              _tmpOutputFileName = null;
            } else {
              _tmpOutputFileName = _cursor.getString(_cursorIndexOfOutputFileName);
            }
            final Long _tmpOutputFileSize;
            if (_cursor.isNull(_cursorIndexOfOutputFileSize)) {
              _tmpOutputFileSize = null;
            } else {
              _tmpOutputFileSize = _cursor.getLong(_cursorIndexOfOutputFileSize);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final Integer _tmpPageCount;
            if (_cursor.isNull(_cursorIndexOfPageCount)) {
              _tmpPageCount = null;
            } else {
              _tmpPageCount = _cursor.getInt(_cursorIndexOfPageCount);
            }
            final boolean _tmpOcrUsed;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOcrUsed);
            _tmpOcrUsed = _tmp != 0;
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final String _tmpFilePath;
            if (_cursor.isNull(_cursorIndexOfFilePath)) {
              _tmpFilePath = null;
            } else {
              _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            }
            final String _tmpOutputPath;
            if (_cursor.isNull(_cursorIndexOfOutputPath)) {
              _tmpOutputPath = null;
            } else {
              _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            }
            final boolean _tmpIsPremium;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsPremium);
            _tmpIsPremium = _tmp_1 != 0;
            _item = new ConversionEntity(_tmpId,_tmpConversionId,_tmpOriginalFileName,_tmpOriginalFileSize,_tmpOutputFileName,_tmpOutputFileSize,_tmpStatus,_tmpPageCount,_tmpOcrUsed,_tmpErrorMessage,_tmpLanguage,_tmpCreatedAt,_tmpCompletedAt,_tmpFilePath,_tmpOutputPath,_tmpIsPremium);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getByConversionId(final String conversionId,
      final Continuation<? super ConversionEntity> $completion) {
    final String _sql = "SELECT * FROM conversions WHERE conversion_id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, conversionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ConversionEntity>() {
      @Override
      @Nullable
      public ConversionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfConversionId = CursorUtil.getColumnIndexOrThrow(_cursor, "conversion_id");
          final int _cursorIndexOfOriginalFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "original_file_name");
          final int _cursorIndexOfOriginalFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "original_file_size");
          final int _cursorIndexOfOutputFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "output_file_name");
          final int _cursorIndexOfOutputFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "output_file_size");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfPageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "page_count");
          final int _cursorIndexOfOcrUsed = CursorUtil.getColumnIndexOrThrow(_cursor, "ocr_used");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "error_message");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completed_at");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "file_path");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "output_path");
          final int _cursorIndexOfIsPremium = CursorUtil.getColumnIndexOrThrow(_cursor, "is_premium");
          final ConversionEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpConversionId;
            _tmpConversionId = _cursor.getString(_cursorIndexOfConversionId);
            final String _tmpOriginalFileName;
            _tmpOriginalFileName = _cursor.getString(_cursorIndexOfOriginalFileName);
            final long _tmpOriginalFileSize;
            _tmpOriginalFileSize = _cursor.getLong(_cursorIndexOfOriginalFileSize);
            final String _tmpOutputFileName;
            if (_cursor.isNull(_cursorIndexOfOutputFileName)) {
              _tmpOutputFileName = null;
            } else {
              _tmpOutputFileName = _cursor.getString(_cursorIndexOfOutputFileName);
            }
            final Long _tmpOutputFileSize;
            if (_cursor.isNull(_cursorIndexOfOutputFileSize)) {
              _tmpOutputFileSize = null;
            } else {
              _tmpOutputFileSize = _cursor.getLong(_cursorIndexOfOutputFileSize);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final Integer _tmpPageCount;
            if (_cursor.isNull(_cursorIndexOfPageCount)) {
              _tmpPageCount = null;
            } else {
              _tmpPageCount = _cursor.getInt(_cursorIndexOfPageCount);
            }
            final boolean _tmpOcrUsed;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOcrUsed);
            _tmpOcrUsed = _tmp != 0;
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final String _tmpFilePath;
            if (_cursor.isNull(_cursorIndexOfFilePath)) {
              _tmpFilePath = null;
            } else {
              _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            }
            final String _tmpOutputPath;
            if (_cursor.isNull(_cursorIndexOfOutputPath)) {
              _tmpOutputPath = null;
            } else {
              _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            }
            final boolean _tmpIsPremium;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsPremium);
            _tmpIsPremium = _tmp_1 != 0;
            _result = new ConversionEntity(_tmpId,_tmpConversionId,_tmpOriginalFileName,_tmpOriginalFileSize,_tmpOutputFileName,_tmpOutputFileSize,_tmpStatus,_tmpPageCount,_tmpOcrUsed,_tmpErrorMessage,_tmpLanguage,_tmpCreatedAt,_tmpCompletedAt,_tmpFilePath,_tmpOutputPath,_tmpIsPremium);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getById(final long id, final Continuation<? super ConversionEntity> $completion) {
    final String _sql = "SELECT * FROM conversions WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ConversionEntity>() {
      @Override
      @Nullable
      public ConversionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfConversionId = CursorUtil.getColumnIndexOrThrow(_cursor, "conversion_id");
          final int _cursorIndexOfOriginalFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "original_file_name");
          final int _cursorIndexOfOriginalFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "original_file_size");
          final int _cursorIndexOfOutputFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "output_file_name");
          final int _cursorIndexOfOutputFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "output_file_size");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfPageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "page_count");
          final int _cursorIndexOfOcrUsed = CursorUtil.getColumnIndexOrThrow(_cursor, "ocr_used");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "error_message");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completed_at");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "file_path");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "output_path");
          final int _cursorIndexOfIsPremium = CursorUtil.getColumnIndexOrThrow(_cursor, "is_premium");
          final ConversionEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpConversionId;
            _tmpConversionId = _cursor.getString(_cursorIndexOfConversionId);
            final String _tmpOriginalFileName;
            _tmpOriginalFileName = _cursor.getString(_cursorIndexOfOriginalFileName);
            final long _tmpOriginalFileSize;
            _tmpOriginalFileSize = _cursor.getLong(_cursorIndexOfOriginalFileSize);
            final String _tmpOutputFileName;
            if (_cursor.isNull(_cursorIndexOfOutputFileName)) {
              _tmpOutputFileName = null;
            } else {
              _tmpOutputFileName = _cursor.getString(_cursorIndexOfOutputFileName);
            }
            final Long _tmpOutputFileSize;
            if (_cursor.isNull(_cursorIndexOfOutputFileSize)) {
              _tmpOutputFileSize = null;
            } else {
              _tmpOutputFileSize = _cursor.getLong(_cursorIndexOfOutputFileSize);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final Integer _tmpPageCount;
            if (_cursor.isNull(_cursorIndexOfPageCount)) {
              _tmpPageCount = null;
            } else {
              _tmpPageCount = _cursor.getInt(_cursorIndexOfPageCount);
            }
            final boolean _tmpOcrUsed;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOcrUsed);
            _tmpOcrUsed = _tmp != 0;
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final String _tmpFilePath;
            if (_cursor.isNull(_cursorIndexOfFilePath)) {
              _tmpFilePath = null;
            } else {
              _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            }
            final String _tmpOutputPath;
            if (_cursor.isNull(_cursorIndexOfOutputPath)) {
              _tmpOutputPath = null;
            } else {
              _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            }
            final boolean _tmpIsPremium;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsPremium);
            _tmpIsPremium = _tmp_1 != 0;
            _result = new ConversionEntity(_tmpId,_tmpConversionId,_tmpOriginalFileName,_tmpOriginalFileSize,_tmpOutputFileName,_tmpOutputFileSize,_tmpStatus,_tmpPageCount,_tmpOcrUsed,_tmpErrorMessage,_tmpLanguage,_tmpCreatedAt,_tmpCompletedAt,_tmpFilePath,_tmpOutputPath,_tmpIsPremium);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM conversions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCountSince(final long sinceTimestamp,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM conversions WHERE created_at >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sinceTimestamp);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
