/**
    QuasselDroid - Quassel client for Android
 	Copyright (C) 2010 Frederik M. J. Vestre
 	Copyright (C) 2011 Martin Sandsmark <martin.sandsmark@kde.org>

    This program is free software: you can redistribute it and/or modify it
    under the terms of the GNU General Public License as published by the Free
    Software Foundation, either version 3 of the License, or (at your option)
    any later version, or under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either version 2.1 of
    the License, or (at your option) any later version.

 	This program is distributed in the hope that it will be useful,
 	but WITHOUT ANY WARRANTY; without even the implied warranty of
 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 	GNU General Public License for more details.

    You should have received a copy of the GNU General Public License and the
    GNU Lesser General Public License along with this program.  If not, see
    <http://www.gnu.org/licenses/>.
 */


package com.lekebilen.quasseldroid.qtcomm.serializers;

import java.io.IOException;

import com.lekebilen.quasseldroid.qtcomm.DataStreamVersion;
import com.lekebilen.quasseldroid.qtcomm.QDataInputStream;
import com.lekebilen.quasseldroid.qtcomm.QDataOutputStream;
import com.lekebilen.quasseldroid.qtcomm.QMetaTypeSerializer;

public class UnsignedInteger implements QMetaTypeSerializer<Long> {
	private int size = 0; // Bits
	
	public UnsignedInteger(int size) {
		this.size = size;
	}
	@Override
	public void serialize(QDataOutputStream stream, Long data,
			DataStreamVersion version) throws IOException {
		stream.writeUInt(data, this.size);
	}

	@Override
	public Long unserialize(QDataInputStream stream, DataStreamVersion version)
			throws IOException {
		return stream.readUInt(this.size);
	}
}
