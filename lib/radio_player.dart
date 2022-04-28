/*
 *  radio_player.dart
 *
 *  Created by Ilya Chirkunov <xc@yar.net> on 28.12.2020.
 */

import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class RadioPlayer {
  static const _methodChannel = MethodChannel('radio_player');
  static const _metadataEvents = EventChannel('radio_player/metadataEvents');
  static const _stateEvents = EventChannel('radio_player/stateEvents');
  static const _loadingEvents = EventChannel('radio_player/loadingEvents');
  static const _notificationEvents = EventChannel('radio_player/notificationEvents');

  static const _defaultArtworkChannel = BasicMessageChannel("radio_player/setArtwork", BinaryCodec());
  static const _metadataArtworkChannel = BasicMessageChannel("radio_player/getArtwork", BinaryCodec());

  Stream<bool>? _stateStream;
  Stream<List<String>>? _metadataStream;
  Stream<String>? _loadingStream;
  Stream<String>? _notifcationStream;

  /// Configure channel
  Future<void> setMediaItem(String title, String url, [String? image]) async {
    await Future.delayed(Duration(milliseconds: 500));
    await _methodChannel.invokeMethod('set', [title, url]);
    if (image != null) setDefaultArtwork(image);
  }

  Future<void> play() async {
    await _methodChannel.invokeMethod('play');
  }

  Future<void> pause() async {
    await _methodChannel.invokeMethod('pause');
  }
  Future<void> stop() async {
    await _methodChannel.invokeMethod('stopradio');
  }

  /// Set default artwork
  Future<void> setDefaultArtwork(String image) async {
    await rootBundle.load(image).then((value) {
      _defaultArtworkChannel.send(value);
    });
  }

  /// Get artwork from metadata
  Future<Image?> getMetadataArtwork() async {
    final byteData = await _metadataArtworkChannel.send(ByteData(0));
    if (byteData == null) return null;

    return Image.memory(
      byteData.buffer.asUint8List(),
      key: UniqueKey(),
      fit: BoxFit.cover,
    );
  }

  /// Get the playback state stream.
  Stream<bool> get stateStream {
    _stateStream ??= _stateEvents.receiveBroadcastStream().map<bool>((value) => value);

    return _stateStream!;
  }

  /// Get the metadata stream.
  Stream<List<String>> get metadataStream {
    _metadataStream ??=
        _metadataEvents.receiveBroadcastStream().map((metadata) {
          return metadata.map<String>((value) => value as String).toList();
        });

    return _metadataStream!;
  }
  /// Get the loading stream.
  Stream<String> get loadingStream {
    _loadingStream ??= _loadingEvents.receiveBroadcastStream().map<String>((value) => value);

    return _loadingStream!;
  }
  /// Get the notification action stream.
  Stream<String> get notificationStream{
    _notifcationStream ??= _notificationEvents.receiveBroadcastStream().map<String>((value) => value);

    return _notifcationStream!;
  }

}