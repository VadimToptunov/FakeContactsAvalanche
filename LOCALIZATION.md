# Localization Guide

This application supports automatic device language detection with fallback to English.

## Supported Languages

### LTR (Left-to-Right) Languages

| Language | Code | Direction | Status |
|----------|------|-----------|--------|
| English | `en` (default) | LTR ⬅️ | ✅ Complete |
| Russian | `ru` | LTR ⬅️ | ✅ Complete |
| Ukrainian | `uk` | LTR ⬅️ | ✅ Complete |
| Spanish | `es` | LTR ⬅️ | ✅ Complete |

### RTL (Right-to-Left) Languages ➡️

| Language | Code | Direction | Status |
|----------|------|-----------|--------|
| Arabic | `ar` | **RTL ➡️** | ✅ Complete |
| Hebrew | `iw` | **RTL ➡️** | ✅ Complete |
| Persian (Farsi) | `fa` | **RTL ➡️** | ✅ Complete |

**For all other languages**, the app automatically uses the English version (fallback).

## File Structure

```
app/src/main/res/
├── values/              # English (default) - used for all unsupported languages
│   └── strings.xml
│
├── LTR Languages (Left-to-Right)
├── values-ru/           # Russian
│   └── strings.xml
├── values-uk/           # Ukrainian
│   └── strings.xml
├── values-es/           # Spanish
│   └── strings.xml
│
└── RTL Languages (Right-to-Left) ➡️
    ├── values-ar/       # Arabic
    │   └── strings.xml
    ├── values-iw/       # Hebrew
    │   └── strings.xml
    └── values-fa/       # Persian (Farsi)
        └── strings.xml
```

## How Localization Works

Android automatically selects the correct `strings.xml` file based on the device language:

- If device language is **Russian** → uses `values-ru/strings.xml`
- If device language is **Ukrainian** → uses `values-uk/strings.xml`
- If device language is **Spanish** → uses `values-es/strings.xml`
- If device language is **Arabic** → uses `values-ar/strings.xml`
- If device language is **Hebrew** → uses `values-iw/strings.xml`
- If device language is **Persian** → uses `values-fa/strings.xml`
- For all other languages → uses `values/strings.xml` (English)

### Fallback Mechanism

If there is no dedicated folder for the device language, Android automatically uses `values/` (English):

```
Example: Device in German 🇩🇪
1. Android searches for: values-de/strings.xml
2. Not found ❌
3. Uses: values/strings.xml (English) ✅
```

This guarantees the app **always works**, even if the language is not directly supported.

### Fallback Examples

| Device Language | File Used | Direction | Note |
|----------------|-----------|-----------|------|
| 🇬🇧 English | `values/strings.xml` | LTR ⬅️ | Default |
| 🇷🇺 Russian | `values-ru/strings.xml` | LTR ⬅️ | Exact match |
| 🇺🇦 Ukrainian | `values-uk/strings.xml` | LTR ⬅️ | Exact match |
| 🇪🇸 Spanish | `values-es/strings.xml` | LTR ⬅️ | Exact match |
| 🇸🇦 Arabic | `values-ar/strings.xml` | **RTL ➡️** | Exact match |
| 🇮🇱 Hebrew | `values-iw/strings.xml` | **RTL ➡️** | Exact match |
| 🇮🇷 Persian | `values-fa/strings.xml` | **RTL ➡️** | Exact match |
| 🇩🇪 German | `values/strings.xml` | LTR ⬅️ | Fallback (English) |
| 🇫🇷 French | `values/strings.xml` | LTR ⬅️ | Fallback (English) |
| 🇨🇳 Chinese | `values/strings.xml` | LTR ⬅️ | Fallback (English) |
| 🇯🇵 Japanese | `values/strings.xml` | LTR ⬅️ | Fallback (English) |

## RTL (Right-to-Left) Support ➡️

The application **fully supports RTL languages** (Arabic, Hebrew, Persian).

### What RTL Support Includes

1. ✅ **`android:supportsRtl="true"`** in AndroidManifest.xml
2. ✅ **Start/End instead of Left/Right** in layouts
3. ✅ **Automatic UI mirroring** of UI elements
4. ✅ **Proper text direction** from right to left
5. ✅ **Plurals for Arabic** (6 forms: zero, one, two, few, many, other)

### How RTL Works

```
LTR (English, Russian):        RTL (Arabic, Hebrew):
┌─────────────────────┐       ┌─────────────────────┐
│ [Label]       [123] │  →    │ [321]       [Label] │
│ ◄ Back              │       │              Back ► │
└─────────────────────┘       └─────────────────────┘
```

Android **automatically** mirrors the UI when the device language is RTL!

### Testing RTL

**Method 1: Change Device Language**
```
Settings → Languages → Add Arabic/Hebrew
```

**Method 2: Force RTL in Developer Options**
```
Settings → Developer Options → Force RTL layout direction
```

**Method 3: ADB Command**
```bash
# Enable Arabic
adb shell "setprop persist.sys.locale ar-SA && stop && start"

# Enable Hebrew
adb shell "setprop persist.sys.locale iw-IL && stop && start"
```

## Adding a New Language

### For LTR Language (Left-to-Right)

1. Create directory `values-{language_code}/` in `app/src/main/res/`
2. Copy `values/strings.xml` to the new directory
3. Translate all strings
4. Update this file by adding the new language to the table

Examples of language codes:
- German: `values-de`
- French: `values-fr`
- Italian: `values-it`
- Chinese: `values-zh`

### For RTL Language (Right-to-Left) ➡️

Same as above, but ensure that:
1. ✅ `android:supportsRtl="true"` is enabled in manifest
2. ✅ Layout uses `start/end` (not `left/right`)
3. ✅ Proper plurals are added for the language

Examples of RTL languages:
- Urdu: `values-ur`
- Uyghur: `values-ug`

## Implementation Details

### Plurals for Russian

Russian uses 4 plural forms:
- `one` - 1 contact, 21 contacts, 31 contacts...
- `few` - 2-4 contacts, 22-24 contacts...
- `many` - 5-20 contacts, 25-30 contacts...
- `other` - 1.5 contacts, 2.3 contacts...

Example:
```xml
<plurals name="contacts_created">
    <item quantity="one">Created %d contact</item>
    <item quantity="few">Created %d contacts</item>
    <item quantity="many">Created %d contacts</item>
    <item quantity="other">Created %d contacts</item>
</plurals>
```

### Plurals for Arabic

Arabic uses 6 plural forms:
- `zero` - 0 contacts
- `one` - 1 contact
- `two` - 2 contacts
- `few` - 3-10 contacts
- `many` - 11-99 contacts
- `other` - 100+ contacts

Example:
```xml
<plurals name="contacts_created">
    <item quantity="zero">No contacts created</item>
    <item quantity="one">Created 1 contact</item>
    <item quantity="two">Created 2 contacts</item>
    <item quantity="few">Created %d contacts</item>
    <item quantity="many">Created %d contacts</item>
    <item quantity="other">Created %d contacts</item>
</plurals>
```

### Formatted Strings

For strings with parameters, use Java formatting:
- `%d` - integer
- `%1$d`, `%2$d` - positional parameters

Example:
```xml
<string name="progress_creating">Creating contacts: %1$d / %2$d</string>
```

## Testing Localization

### On Emulator

1. Settings → System → Languages & input → Languages
2. Add the desired language and move it to the first position
3. Restart the application

### On Real Device

1. Settings → Language and input → Languages
2. Select the desired language
3. Restart the application

### Quick Switch for Development

In Android Studio:
1. Run → Edit Configurations
2. In the "General" tab find "Language"
3. Select the language for testing

## Common Issues and Solutions

### Problem 1: UI Doesn't Mirror
**Cause:** `android:supportsRtl="false"` or missing
**Solution:** Ensure `android:supportsRtl="true"` in AndroidManifest.xml

### Problem 2: Some Elements Don't Flip
**Cause:** Using `left/right` instead of `start/end`
**Solution:** In layout files replace:
- `layout_marginLeft` → `layout_marginStart`
- `layout_marginRight` → `layout_marginEnd`
- `paddingLeft` → `paddingStart`
- `paddingRight` → `paddingEnd`

### Problem 3: Text Not Translated
**Cause:** Hardcoded strings in code/layout
**Solution:** All strings should be in `strings.xml`:
```xml
<!-- Bad -->
android:text="Generate"

<!-- Good -->
android:text="@string/generate_btn_text"
```

### Problem 4: Wrong Plural Count
**Cause:** Arabic requires 6 forms, not 2
**Solution:** Use all forms:
```xml
<plurals name="contacts_created">
    <item quantity="zero">...</item>
    <item quantity="one">...</item>
    <item quantity="two">...</item>
    <item quantity="few">...</item>
    <item quantity="many">...</item>
    <item quantity="other">...</item>
</plurals>
```

## Useful Links

- [Android Localization Guide](https://developer.android.com/guide/topics/resources/localization)
- [Android RTL Guide](https://developer.android.com/training/basics/supporting-devices/languages#CreateAlternatives)
- [Material Design RTL](https://m2.material.io/design/usability/bidirectionality.html)
- [Multilingual Support Best Practices](https://developer.android.com/guide/topics/resources/multilingual-support)

## Summary

Your application is **fully multilingual**:
- ✅ 7 languages (4 LTR + 3 RTL)
- ✅ Automatic UI mirroring for RTL
- ✅ Correct plurals for all languages
- ✅ Fallback to English for unsupported languages
- ✅ Production-ready

**Ready for worldwide release!** 🚀
