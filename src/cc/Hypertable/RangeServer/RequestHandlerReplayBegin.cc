/** -*- c++ -*-
 * Copyright (C) 2007-2012 Hypertable, Inc.
 *
 * This file is part of Hypertable.
 *
 * Hypertable is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; version 3 of the
 * License, or any later version.
 *
 * Hypertable is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

#include "Common/Compat.h"
#include "AsyncComm/ResponseCallback.h"

#include "RequestHandlerReplayBegin.h"
#include "RangeServer.h"

using namespace Hypertable;
using namespace Serialization;

/**
 *
 */
void RequestHandlerReplayBegin::run() {
  ResponseCallback cb(m_comm, m_event_ptr);
  const uint8_t *decode_ptr = m_event_ptr->payload;
  size_t decode_remain = m_event_ptr->payload_len;

  try {
    uint16_t group = decode_i16(&decode_ptr, &decode_remain);
    m_range_server->replay_begin(&cb, group);
  }
  catch (Exception &e) {
    HT_ERROR_OUT << e << HT_END;
    cb.error(e.code(), e.what());
  }
}
