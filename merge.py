import pickle
import pandas as pd

df = pd.read_csv('user.csv')
with open('keypoints', 'rb') as file:
    data = pickle.load(file)

store = {}
keys = set()
for gloss in df.gloss.unique():
    gloss_store = {}
    store[gloss] = gloss_store

    df2 = df[df.gloss == gloss]
    for variant in df2.variant.unique():
        variant_store = {}
        gloss_store[variant] = variant_store

        df3 = df2[df2.variant == variant]
        for row in df3.iterrows():
            row = row[1]
            row_key = f'{row.session}-{row.scene}-{row.start}-{row.end}'
            row_store = []
            variant_store[row_key] = row_store

            for frame_no in range(row.start, row.end + 1):
                key = f'{row.session}-{row.scene}-{frame_no}'
                keys.add(key)
                row_store.append(data[key]['people'][0])

print('Keys Equal:', keys == data.keys())

with open('keypoints2', 'wb') as file:
    pickle.dump(store, file)